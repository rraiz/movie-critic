import os

base_path = 'outputs'

# Open files in the "complete" directory
movies_file = open(os.path.join(base_path, 'movies.tsv'), 'a', encoding='utf-8')
shows_file = open(os.path.join(base_path, 'shows.tsv'), 'a', encoding='utf-8')
directors_file = open(os.path.join(base_path, 'directors.tsv'), 'a', encoding='utf-8')
writers_file = open(os.path.join(base_path, 'writers.tsv'), 'a', encoding='utf-8')
actors_file = open(os.path.join(base_path, 'actors.tsv'), 'a', encoding='utf-8')
companies_file = open(os.path.join(base_path, 'production_companies.tsv'), 'a', encoding='utf-8')

# Assuming headers need to be written first if files are empty
print('tconst\ttitleType\ttitle\tstartYear\truntimeMinutes\tgenres\tdescription\tcoverURL\tdirectors\tcreators\tstars\tproductionCompanies\tmpafRating\timdbRating\tvotes', file=movies_file)          
print('tconst\ttitleType\ttitle\tstartYear\tendYear\truntimeMinutes\tgenres\tdescription\tcoverURL\tdirectors\tcreators\tstars\tproductionCompanies\tmpafRating\timdbRating\tvotes', file=shows_file)  
print('tconst\tname\tmedias', file=directors_file)
print('tconst\tname\tmedias', file=writers_file)
print('tconst\tname\tmedias', file=actors_file)
print('tconst\tname\tmedias', file=companies_file)