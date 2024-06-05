import "../film.css"

export default function Backdrop({backdropPath}) {

    return (
        <div>
        {backdropPath ? (
            <div 
                className="mx-auto backdrop-fade bg-cover bg-[center_-30px] h-[400px]" 
                style={{ backgroundImage: `url(https://image.tmdb.org/t/p/original/${backdropPath})` }}>
            </div>
            ) : (
            <div>
                <p className='py-5'></p>
            </div>
            )}
        </div>
    );
}