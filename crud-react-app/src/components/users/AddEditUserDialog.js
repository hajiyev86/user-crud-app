import React, { useState, useEffect } from "react";
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import Slide from '@mui/material/Slide';
import { useTranslation } from "react-i18next";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Typography from "@mui/material/Typography";
import { isEmail } from "validator";

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="down" ref={ref} {...props} />;
});

const initialUserData = {
    phoneNumber: "",
    email: "",
    name: ""
};

const AddEditUserDialog = (props) => {
    const { openDialog, handleCloseDialog, showMessageDialog, selectedUser } = props;
    const { t } = useTranslation();
    const [errors, setErrors] = useState({});
    const [userData, setUserData] = useState(initialUserData);

    useEffect(() => {
        setErrors({});
        setUserData(initialUserData);
    }, []);

    useEffect(() => {
        if (selectedUser) {
            setUserData({
                name: selectedUser.name || '',
                email: selectedUser.email || '',
                phoneNumber: selectedUser.phoneNumber || ''
            });
        }
    }, [selectedUser]);


    function isValid() {
        let formIsValid = true;

        setErrors({});
        if (!userData.name) {
            formIsValid = false;
            setErrors((prevState) => ({
                ...prevState,
                name: t("user.name") + " " + t("error.isRequiredSuffix")
            }));
        } else if (typeof userData.name !== "undefined") {
            if (!userData.name.match(/^[a-zA-Z ]+$/)) {
                formIsValid = false;
                setErrors((prevState) => ({
                    ...prevState,
                    name: t("user.name") + " " + t("error.onlyLettersSuffix")
                }));
            } else if (userData.name.trim().length < 3) {
                formIsValid = false;
                setErrors((prevState) => ({
                    ...prevState,
                    name: t("user.name") + " " + t("error.lengthMinThan3Suffix")
                }));
            }
        }

        if (!userData.phoneNumber) {
            formIsValid = false;
            setErrors((prevState) => ({
                ...prevState,
                phoneNumber: t("user.phoneNumber") + " " + t("error.isRequiredSuffix")
            }));
        } else {
            if (!isValidPhoneNumber(userData.phoneNumber)) {
                formIsValid = false;
                setErrors((prevState) => ({
                    ...prevState,
                    phoneNumber: t("error.phoneNumberIsNotValid")
                }));
            }
        }
        if (!userData.email) {
            formIsValid = false;
            setErrors((prevState) => ({
                ...prevState,
                email: t("user.email") + " " + t("error.isRequiredSuffix")
            }));
        } else {
            if (!isEmail(userData.email)) {
                formIsValid = false;
                setErrors((prevState) => ({
                    ...prevState,
                    email: t("error.emailIsNotValid")
                }));
            }
        }



        if (!formIsValid) {
            window.scrollTo({
                top: 0,
                behavior: "smooth",
            });
            showMessageDialog("error", t("error.fillAllRequiredFields"), null);
        }

        return formIsValid;
    }

    function isValidPhoneNumber(value) {
        // var er = /^\+?\d+$/;
        // return er.test(value);

        // Allow a plus sign followed by numbers 0-9, with optional spaces or dashes
        var er = /^\+?(\d[\d\- ]+)$/;
        // Remove spaces and dashes for validation
        var cleanedValue = value.replace(/[-\s]/g, '');
        return er.test(cleanedValue);
    }

    const handleChangeDataByFieldOnlyAlphabet = (object, field, value) => {
        const alpabet = value.toUpperCase().replace(/[^A-Za-z ]/ig, "");
        if (object === "userData") {
            setUserData((prevState) => {
                return {
                    ...prevState,
                    [field]: alpabet,
                };
            });
        }
        setErrors((prevState) => ({
            ...prevState,
            [field]: null
        }));
    };

    const handleChangeDataByField = (object, field, value) => {
        if (object === "userData") {
            if (field === "phoneNumber") {
                //const plusAndOnlyNums = value.replace(/[^\+\d]/g, '');
                const plusAndOnlyNums = value.replace(/[^+\d\- ]/g, '');
                setUserData((prevState) => {
                    return {
                        ...prevState,
                        [field]: plusAndOnlyNums,
                    };
                });
            } else {
                setUserData((prevState) => {
                    return {
                        ...prevState,
                        [field]: value,
                    };
                });
            }
        }
        setErrors((prevState) => ({
            ...prevState,
            [field]: null
        }));
    };



    return (
        <Dialog fullWidth={true}
            maxWidth={"sm"}
            open={openDialog}
            onClose={() => handleCloseDialog(0)}
            // onBackdropClick="false"
            TransitionComponent={Transition}>
            <DialogTitle className="darkBoldTextOnWhiteBoard">
                {selectedUser ? t("updateUser") : t("addUser")}
            </DialogTitle>
            <DialogContent dividers>
                <Box component="form" noValidate autoComplete="off">
                    <div className="row">

                        <div className="col-sm-12" style={{ padding: "15px" }}>
                            <TextField
                                fullWidth
                                focused
                                label={<Typography>{t("user.name")} <span style={{ color: "#cc0066", fontWeight: "bold" }}>*</span></Typography>}
                                variant="standard"
                                name="name"
                                value={userData.name}
                                onChange={e => handleChangeDataByFieldOnlyAlphabet('userData', e.target.name, e.target.value)}
                                helperText={<span style={{ color: "#cc0066" }}>{errors.name}</span>}
                            />
                        </div>

                        <div className="col-sm-12" style={{ padding: "15px" }}>
                            <TextField
                                fullWidth
                                focused
                                label={<Typography>{t("user.email")} <span style={{ color: "#cc0066", fontWeight: "bold" }}>*</span></Typography>}
                                variant="standard"
                                name="email"
                                value={userData.email}
                                onChange={e => handleChangeDataByField('userData', e.target.name, e.target.value)}
                                helperText={<span style={{ color: "#cc0066" }}>{errors.email}</span>}
                            />
                        </div>
                        <div className="col-sm-12" style={{ padding: "15px" }}>
                            <TextField
                                fullWidth
                                focused
                                label={<Typography>{t("user.phoneNumber")} <span style={{ color: "#cc0066", fontWeight: "bold" }}>*</span></Typography>}
                                variant="standard"
                                name="phoneNumber"
                                value={userData.phoneNumber}
                                onChange={e => handleChangeDataByField('userData', e.target.name, e.target.value)}
                                helperText={<span style={{ color: "#cc0066" }}>{errors.phoneNumber}</span>}
                            />
                        </div>


                    </div>
                </Box>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => {
                    if (!isValid()) {
                        return;
                    }
                    handleCloseDialog(1, userData);
                }} style={{ marginLeft: "10px", textTransform: "none", marginBottom: "10px" }} variant="contained">{selectedUser ? t("update") : t("add")}</Button>
                <Button onClick={() => handleCloseDialog(0)} style={{ marginLeft: "10px", textTransform: "none", marginBottom: "10px" }} variant="contained">{t("close")}</Button>
            </DialogActions>
        </Dialog>
    );
};

export default AddEditUserDialog;


