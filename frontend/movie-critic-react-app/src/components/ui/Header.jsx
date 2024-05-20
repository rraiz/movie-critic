import styles from './Navbar.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

function Header() {
    return (
        <header>
            <nav className={styles.navbar}>
                <h1>Movie Critic</h1>
                <ul className={styles.top}>
                    <li><a href="/home">Home</a></li>
                    <li><a href="#">Films</a></li>
                    <li><a href="/about">About</a></li>
                    <li><a href="/sign-in">Sign In</a></li>
                    <li>
                        <div className={styles.searchContainer}>
                            <input type="text" placeholder="" />
                            <button type="button" className={styles.searchButton}>
                                <FontAwesomeIcon className={styles.searchIcon} icon={faMagnifyingGlass} />
                            </button>
                        </div>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default Header;
