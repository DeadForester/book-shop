import {useAuthContext} from "../hooks/useAuthContext.js";

const Login = () => {
    const {setIsAuth} = useAuthContext();

    const login = (e) => {
        e.preventDefault();
        setIsAuth(true);
        localStorage.setItem('auth', 'true');
    };

    return (
        <div>
            <h1>Страница входа</h1>
            <form onSubmit={login}>
                <MyInput type="text" placeholder="Введите логин"/>
                <MyInput type="password" placeholder="Введите пароль"/>
                <MyButton type='submit'>
                    Войти
                </MyButton>
            </form>
        </div>
    );
};

export default Login;