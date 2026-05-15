import {useContext} from "react";
import {AuthContext} from "../context/auth.js";

export const useAuthContext = () => useContext(AuthContext);