import React, { useState, useEffect, useRef } from 'react';
import './Login.css';
import bcrypt from 'bcryptjs';

function Login() {
    const [user, setUser] = useState({});

    useEffect(() => {
        const fetchResult = async () => {
            fetch('/express/user')
                .then(res => res.json())
                .then(res => setUser(res));
        }
        fetchResult();
    }, [user.id]);

    const loginRef = useRef();
    // const passwordRef = useRef();

    function handleLoginForm() {
        const login = loginRef.current.value;
        // const password = passwordRef.current.value;
        // const hashedPassword = bcrypt.hashSync(password, 'VeryStrongSalt!');

        fetch('/express/login', {
            method: 'POST',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              login: login,
            //   password: hashedPassword,
            })
        });
    }
    
    if (user.id !== undefined) {
        return <p>Welcome back, {user.name}!</p>
    }
    return (
        <div className="Login">
            <form>
                <div>
                    <p>Login: </p>
                    <input type="text" name="login" ref={loginRef}/>
                </div>
                {/* <div>
                    <p>Password: </p>
                    <input type="password" name="password" ref={passwordRef}/>
                </div> */}
                <div>
                    <button type="submit" onClick={() => handleLoginForm()}>
                        Login
                    </button>
                </div>
            </form>
        </div>
    );
}

export default Login;
