import requests
from bs4 import BeautifulSoup
import csv

def fetch_movies(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')
    
    results = soup.find('div', class_='lister-list')
    movie_items = results.find_all('div', class_='lister-item mode-detail')

    if len(movie_items) == 0:
        print('No results found')
        return False 
    
    for movie in movie_items:
        title = movie.find('h3', class_='lister-item-header') # Movie title
        title = title.a.text if title is not None else None

        year = movie.find('span', class_='lister-item-year') # Year movie was released
        year = year.text if year is not None else None

        rating = movie.find('span', class_='ipl-rating-star__rating') # Movie rating out of 10
        rating = rating.text if rating is not None else None

        length = movie.find('span', class_='runtime') # Movie length in minutes
        length = length.text if length is not None else None

        genre = movie.find('span', class_='genre') # Movie genre
        genre = genre.text if genre is not None else None

        description = movie.find('p', class_='') # Movie description
        description = description.text if description is not None else None

        credits_ = movie.find_all('p', class_='text-muted text-small')[1].text.strip().split('|')
        director = credits_[0].split(':')[1].strip().replace("\n", "").split(',') # Movie director(s)
        stars = credits_[1].split(':')[1].replace("\n", "").split(',') # Movie star(s)

        rated = movie.find('span', class_='certificate')# Movie rating (G, PG, PG-13, R, etc.)
        rated = rated.text if rated is not None else None

        cover_url = movie.find('img', class_='loadlate')['loadlate'] # URL to movie cover image

        gross = movie.find_all('span', attrs={'name': 'nv'}) # Gross earnings
        gross = gross[1].text if len(gross) == 2 else None

        print(f'Title: {title}' + '\n' + f'Year: {year}' + '\n' + f'Rating: {rating}' + '\n' + f'Length: {length}' + '\n' + f'Genre: {genre}' + '\n' + f'Description: {description}' + '\n' + f'Director(s): {director}' + '\n' + f'Star(s): {stars}' + '\n' + f'Rated: {rated}' + '\n' + f'Cover URL: {cover_url}' + '\n' + f'Gross: {gross}' + '\n' + '---' + '\n')
    return True
        


def getMovieDetails(imdbID):
    data = {}
    
    movie_url = "https://www.imdb.com/title/"+imdbID
    r = requests.get(url=movie_url)
    # Create a BeautifulSoup object
    soup = BeautifulSoup(r.text, 'html.parser')
    jsonData = soup.find('script',{"type":"application/ld+json"})
    print(jsonData.string)


def main():
    getMovieDetails("tt14230458")
    """
    base_url = 'https://www.imdb.com/list/ls527164064/'

    page = 1
    movies_found = True
    while movies_found:
        current_url = f"{base_url}?sort=list_order,asc&st_dt=&mode=detail&page={page}"
        print(current_url)
        movies_found = fetch_movies(current_url)
        page += 1
    """






if __name__ == "__main__":
    main()
