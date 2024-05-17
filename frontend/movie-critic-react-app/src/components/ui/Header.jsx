import './Navbar.module.css'

function Header(){

    return(
        <header>
            <nav class="header">
                <h1>Movie Critic</h1>
                <ul>
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Films</a></li>
                    <li><a href="#">About</a></li>
                    <li><a href="#">Sign In</a></li>
                </ul>
            </nav>
        </header>
    )

}

export default Header;