import {useContext} from "react";
import {AuthContext} from "../auth.js";

export const useAuthContext = () => useContext(AuthContext);