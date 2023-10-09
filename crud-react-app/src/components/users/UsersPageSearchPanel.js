import React from "react";
import { Box, Button, TextField, Typography } from '@mui/material';
import { useTranslation } from "react-i18next";

const SearchPanel = (props) => {
  const { t } = useTranslation();
  const { searchData, handleChangeSearchData, searchUsersActionPerformed, clearSearchActionPerformed } = props;
  return (
    <Box sx={{ margin: "20px 10px" }} >
      <div className="row" style={{ backgroundColor: "#e7ecf1", borderRadius: "5px", padding: "5px" }}>
        <div className="col-sm-12 " style={{ padding: "20px 15px" }}>
          <h4>{t("search.title")}</h4>
        </div>
        <div className="col-12" style={{ padding: "20px 15px" }}>
          <TextField
            fullWidth
            focused
            label={<Typography>{t("user.name")} </Typography>}
            variant="standard"
            name="name"
            value={searchData.name}
            onChange={handleChangeSearchData}
          />
        </div>
        <div className="col-12 " style={{ padding: "20px 15px" }}>
          <TextField
            fullWidth
            focused
            label={<Typography>{t("user.email")}</Typography>}
            variant="standard"
            name="email"
            value={searchData.email}
            onChange={handleChangeSearchData}
          />
        </div>
        <div className="col-12" style={{ padding: "20px 15px" }}>
          <TextField
            fullWidth
            focused
            label={<Typography>{t("user.phoneNumber")} </Typography>}
            variant="standard"
            name="phoneNumber"
            value={searchData.phoneNumber}
            onChange={handleChangeSearchData}
          />
        </div>

        <div className="col-sm-6 " style={{ padding: "20px 15px" }}>
          <Button onClick={clearSearchActionPerformed} variant="outlined" size="large" className="float-start" style={{ textTransform: "none" }}>{t("clear")}</Button>
        </div>
        <div className="col-sm-6 " style={{ padding: "20px 15px" }}>
          <Button onClick={searchUsersActionPerformed} variant="contained" size="large" className="float-end" style={{ textTransform: "none" }}>{t("search")}</Button>
        </div>
      </div>
    </Box>
  )
}

export default SearchPanel