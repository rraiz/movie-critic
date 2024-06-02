import errorPic from './error-pic.jpg';
import './error.css';

export default function Error() {
    return (
        <div className="bg-[#14181d] text-[#d7dae1] text-center min-h-screen flex flex-col items-center">
            <h1 className='text-[50px] font-bold'>404</h1>
            <p className='text-[20px] pb-[25px]'>Page was not found... </p>
            <div className="absolute top-[575px] z-10">
                <h1 className="font-bold text-[50px] p-5">You look lonely...</h1>
                <a 
                    href="/" 
                    className="text-[18px] p-2 bg-[#272828] rounded hover:bg-[#1f1f20]"
                >
                    I can fix that
                </a>
            </div>
            <div>
                <img src={errorPic} className="max-w-screen-md fadeout" alt="Error illustration"></img>
            </div>
        </div>
    );
}
