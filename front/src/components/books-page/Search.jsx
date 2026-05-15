import {TextField} from "@mui/material";

const Search = (props) => {
    const { onChange, value } = props;

    return <TextField
        label="Search"
        type='search'
        value={value}
        onChange={onChange}
        variant="standard"
        fullWidth
        sx={{
            mb: '1.5rem',
        }}
    />;
};

export default Search;