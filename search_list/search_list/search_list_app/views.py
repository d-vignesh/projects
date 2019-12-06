from django.shortcuts import render
from bs4 import BeautifulSoup
import requests
from requests.compat import quote_plus
from .models import Search
import datetime

# Create your views here.

BASE_CRAIGLIST_URL = 'https://bangalore.craigslist.org/search/bbb?query={}'

def home(request):
    return render(request, template_name='search_list_app/home.html')


def new_search(request):
    search_text = request.POST.get('search_text', '')
    search_obj = Search.objects.create(search=search_text, created=datetime.datetime.now())
    search_obj.save()
    # constructing the url version of the search_text
    # eg., if the search text is 'java tutorial' , in a url it should be represented as java+tutorial
    url_search_text = quote_plus(search_text)
    search_url = BASE_CRAIGLIST_URL.format(url_search_text)
    print(search_url)
    page_src = requests.get(search_url)
    page_content = page_src.content
    soup = BeautifulSoup(page_content, 'html.parser')
    post_listings = soup.find_all('li', {'class': 'result-row'})
    final_posting = []
    for post in post_listings:
        post_details = post.find('a', {'class': 'result-title hdrlnk'})
        post_title = post_details.text
        post_url = post_details.get('href', '#')
        item_src = requests.get(post_url)
        item_content = item_src.content
        item_soup = BeautifulSoup(item_content, 'html.parser')
        post_image = item_soup.find('img').get('src')
        print(post_title, post_url, post_image)
        final_posting.append((post_title, post_url, post_image))
        print('-----' * 10)
    context = {
        'search_text': search_text,
        'post_details': final_posting
    }
    return render(request, template_name='search_list_app/new_search.html', context=context)