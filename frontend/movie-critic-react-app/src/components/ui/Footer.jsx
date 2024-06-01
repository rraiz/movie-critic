import styles from './Navbar.module.css'

function Footer() {
    return(
        <footer>
            <nav className={styles.navbar}>
            <p className={styles.bottom}>&copy; {new Date().getFullYear()} Movie Critic. Made by Rayan Raiszadeh. 
            Film data from <a href="https://www.themoviedb.org/">TMDb</a></p>
            </nav>
        </footer>
    )
}

export default Footer;

