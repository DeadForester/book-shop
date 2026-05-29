import { Autocomplete, InputAdornment, TextField } from '@mui/material';
import { FilterList } from '@mui/icons-material';

const AutocompleteSelector = ({ value, onChange, options, label, placeholder }) => {
    return (
        <Autocomplete
            value={value}
            onChange={onChange}
            options={options}
            renderInput={(params) => (
                <TextField
                    {...params}
                    label={label}
                    variant="outlined"
                    size="small"
                    placeholder={placeholder}
                    InputProps={{
                        ...params.InputProps,
                        startAdornment: (
                            <InputAdornment position="start">
                                <FilterList color="action" />
                            </InputAdornment>
                        ),
                    }}
                />
            )}
            sx={{
                minWidth: { xs: '100%', sm: 200 },
                flex: { xs: '1 1 100%', sm: '0 1 auto' },
            }}
            freeSolo={false}
            isOptionEqualToValue={(option, value) => option === value}
        />
    );
};

export default AutocompleteSelector;
