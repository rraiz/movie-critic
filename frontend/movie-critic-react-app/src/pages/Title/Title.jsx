import React, { useState, useEffect } from 'react';


export default function Title() {

    const [media, setMedia] = useState(null);

    // Fetch the data from the API
    useEffect(() => {
        fetch("/api/v1/medias/112637874")
          .then((res) => {
            if (!res.ok) {
              throw new Error('Network response was not ok');
            }
            return res.json();
          })
          .then((data) => {
            setData(data);
            console.log(data);
          });
      }, []);

      if(media)
        {
            // If the media was not updated yet, update it      
            if((media.coverURL == null))
                getMoviePoster(media.title) //gets it from tmdb api
                                            //puts into my database

            else
            {
                //idk yet
            }
        }


    return (
        <>
            <div class="background-container">
                <div class="content">
                    <h1>Oppenheimer</h1>
                    <p>The story of J. Robert Oppenheimer's role in the development of the atomic bomb during World War II.</p>
                </div>
            </div>
            <div>
                <main>
                    <h1>Welcome to Movie Critic</h1>
                    <p>Here you can find reviews of the latest movies, and share your own opinions! Lorem ipsum dolor sit amet consectetur, adipisicing elit. Minima rerum esse quaerat nisi dicta sunt provident assumenda ullam dolore quae cum, earum illum eaque id, voluptatibus perferendis maiores alias laboriosam.</p>
                </main>
            </div>
        </>
    );
}

function getMoviePoster(title) {

    //make a request to the movie database api

}