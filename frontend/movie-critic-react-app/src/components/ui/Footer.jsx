
function Footer() {
    return(
        <footer className="bg-[#14181d] p-6 text-slate-100">
            <div className="border-t border-[#384148] p-4"></div>
            <p className="text-center text-sm">&copy; {new Date().getFullYear()} Movie Critic. Made by Rayan Raiszadeh. 
            Film data from <a className="underline hover:text-gray-300" href="https://www.themoviedb.org/">TMDb</a></p>
        </footer>
    )
}

export default Footer;

