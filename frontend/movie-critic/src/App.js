import React, { useState, useEffect } from 'react';

function App() {
  const [data, setData] = useState(null);

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

  return (
    <div>
      <h1>Media Details</h1>

      {data ? (
        <div>
          <h2>{data.title}</h2>
          <p>Rating: {data.averageRating}</p>
        </div>
      ) : (
        <p>No data available</p>
      )}
    </div>
  );
}

export default App;
