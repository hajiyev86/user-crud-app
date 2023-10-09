import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useTranslation } from "react-i18next";

export default function AlertDialog(props) {
    const { t } = useTranslation();
    const { openDialog, handleCloseDialog, message, title } = props;

    return (

        <Dialog
            open={openDialog}
            onClose={()=>handleCloseDialog(0)}
            aria-labelledby="alert-dialog-title"
            aria-describedby="alert-dialog-description"
        >
            <DialogTitle id="alert-dialog-title">
                {title}
            </DialogTitle>
            <DialogContent>
                <DialogContentText id="alert-dialog-description">
                    {message}
                </DialogContentText>
            </DialogContent>
            <DialogActions>
                <Button onClick={() => handleCloseDialog(0)}>{t("no")}</Button>
                <Button onClick={() => handleCloseDialog(1)} autoFocus> {t("yes")}</Button>
            </DialogActions>
        </Dialog>

    );
}
