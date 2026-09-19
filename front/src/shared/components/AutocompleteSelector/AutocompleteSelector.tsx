import { FilterList } from '@mui/icons-material';
import { Autocomplete, InputAdornment, TextField } from '@mui/material';
import { SyntheticEvent } from 'react';

interface AutocompleteSelectorProps {
    /** Текущее выбранное значение (или null, если ничего не выбрано) */
    value: string | null;
    /** Колбэк, вызывается при выборе нового значения из списка */
    onChange: (event: SyntheticEvent, value: string | null) => void;
    /** Список доступных вариантов для выбора */
    options: string[];
    /** Текст лейбла над полем */
    label: string;
    /** Текст плейсхолдера внутри пустого поля */
    placeholder: string;
}

const AutocompleteSelector = ({
    value,
    onChange,
    options,
    label,
    placeholder,
}: AutocompleteSelectorProps) => {
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
                    slotProps={{
                        ...params.slotProps,
                        input: {
                            startAdornment: (
                                <InputAdornment position="start">
                                    <FilterList color="action" />
                                </InputAdornment>
                            ),
                        },
                    }}
                />
            )}
            sx={{
                minWidth: { xs: '100%', sm: 200 },
                flex: { xs: '1 1 100%', sm: '0 1 auto' },
            }}
            freeSolo={false}
            isOptionEqualToValue={(option, val) => option === val}
        />
    );
};

export default AutocompleteSelector;
