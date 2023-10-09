import React from 'react';
import AppBar from '@mui/material/AppBar';
import PeopleIcon from '@mui/icons-material/People';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom';
import MenuItem from '@mui/material/MenuItem';
import { TextField } from '@mui/material';
import i18next from "i18next";
import { useTranslation } from "react-i18next";
import i18n from "./helpers/i18nextInit"

const Header = () => {
    const { t } = useTranslation();

    var selectedLng = localStorage.getItem("i18nextLng") || "en";
    if (selectedLng !== "en" && selectedLng !== "ru") {
        i18next.changeLanguage('en');
        selectedLng = "en";
    }

    const handleLanguageChange = (e) => {
        i18n.changeLanguage(e.target.value);
    };

    return (
        
        <AppBar position="relative">
            <Toolbar style={{ display: 'flex', justifyContent: 'space-between' }}>
                <Link to="/" style={{ textDecoration: 'none', color: 'inherit', display: 'flex', alignItems: 'center' }}>
                    <PeopleIcon className="d-none d-sm-block" sx={{ marginRight: 1 }} />
                    <Typography variant="h6" color="inherit" noWrap sx={{ lineHeight: '1', fontSize: { xs: '1rem', sm: '1.5rem' } }}>
                        {t("system.name")}
                    </Typography>
                </Link>

                <div>

                    <TextField
                        select
                        className="language-select"
                        value={localStorage.getItem("i18nextLng") || "en"}
                        onChange={handleLanguageChange}

                    >
                        <MenuItem value={"en"}><Typography> <img src="/images/en.svg" style={{ height: "12px" }} alt="az" /> En</Typography></MenuItem>
                        <MenuItem value={"ru"}><Typography> <img src="/images/ru.svg" style={{ height: "12px" }} alt="az" /> Ru</Typography></MenuItem>
                    </TextField>
                </div>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
