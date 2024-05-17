import './Navbar.module.css'

function Footer() {
    return(
        <footer>
            <nav>
            <p className='poop'>&copy; {new Date().getFullYear()} Movie Critic. Made by Rayan Raiszadeh. 
            Film data from <a href="https://developer.imdb.com/non-commercial-datasets/">Imdb</a></p>
            </nav>
        </footer>
    )
}

export default Footer;

