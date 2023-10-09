import React, { useState, useContext } from "react";
import { IconButton, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material';
import { useTranslation } from "react-i18next";
import EditIcon from '@mui/icons-material/Edit';
import ClearIcon from '@mui/icons-material/Clear';
import { Box, Button } from '@mui/material';
import UserDialog from "./AddEditUserDialog";
import AlertDialog from "../helpers/AlertDialog";
import userServices from "../../services/user.services";
import { AppContext } from '../../App';

const UsersPageOperationsPanel = (props) => {
    const { showMessageDialog, showLoading } = useContext(AppContext);
    const { users, usersCount, page, rowsPerPage, handleChangePage, handleChangeRowsPerPage, handleRefreshResult } = props;
    const { t } = useTranslation();
    const [openUserDialog, setOpenUserDialog] = useState(false);
    const [openDeleteAlertDialog, setOpenDeleteAlertDialog] = useState(false);
    const [selectedUser, setSelectedUser] = useState(-1);
    const [deleteMessageText, setDeleteMessageText] = useState("");

    const columns = [
        { id: 'id', label: t("Id"), minWidth: 100 },
        { id: 'name', label: t('user.name'), minWidth: 170 },
        { id: 'email', label: t('user.email'), minWidth: 170 },
        { id: 'phoneNumber', label: t('user.phoneNumber'), minWidth: 170 },
        { id: 'age', label: t('user.age'), minWidth: 120 },
        { id: 'creationDate', label: t('creationDate'), minWidth: 120, align: 'right' }
    ];


    //------------------------Add Edit user Dialog-------------------------------
    const showAddEditUserDialog = (user) => {
        setSelectedUser(user);
        setOpenUserDialog(true);
    };


    const handleCloseAddEditUserDialog = (result, userData) => {
        if (result === 1) {
            showLoading(true);
            if (selectedUser && selectedUser.id) {
                userServices.putUpdateUser(selectedUser.id,userData).then(
                    (response) => {
                        showLoading(false);
                        showMessageDialog("success", t("successfully.updated"));
                        handleRefreshResult();
                    },
                    (error) => {
                        showLoading(false);
                        const errorMessage = error.response?.data?.message || error.message || 'An error occurred.';
                        console.log(errorMessage);
                        showMessageDialog("error", errorMessage);
                    }
                );

            } else {
                userServices.postCreateUser(userData).then(
                    (response) => {
                        showLoading(false);
                        showMessageDialog("success", t("successfully.added"));
                        handleRefreshResult();
                    },
                    (error) => {
                        showLoading(false);
                        const errorMessage = error.response?.data?.message || error.message || 'An error occurred.';
                        console.log(errorMessage);
                        showMessageDialog("error", errorMessage);
                    }
                );
            }
        }
        setOpenUserDialog(false);
    };
    //-----------------End of Add Edit user Dialog-------------------------------




    //----------------------Delete user AlertDialog-------------------------------
    const showDeleteDialogActionPerformed = (user) => {
        setSelectedUser(user);
        setDeleteMessageText(t("delete.user.message", { id: user.id, name: user.name }));
        setOpenDeleteAlertDialog(true);
    };

    const handleCloseDeleteDialog = (result) => {
        if (result === 1) {
            showLoading(true);
            userServices.deleteUser(selectedUser.id).then(
                (response) => {
                    showLoading(false);
                    showMessageDialog("success", t("successfully.deleted"));
                    handleRefreshResult();

                },
                (error) => {
                    showLoading(false);
                    const errorMessage = error.response?.data?.message || error.message || 'An error occurred.';
                    console.log(errorMessage);
                    showMessageDialog("error", errorMessage);
                }
            );
        }
        setOpenDeleteAlertDialog(false);
    };
    //-----------------End of Delete user AlertDialog-------------------------------

    return (
        <>
            {openUserDialog && (<UserDialog
                showMessageDialog={showMessageDialog}
                openDialog={openUserDialog}
                handleCloseDialog={handleCloseAddEditUserDialog}
                selectedUser={selectedUser} />)}
            {openDeleteAlertDialog && (<AlertDialog
                openDialog={openDeleteAlertDialog}
                handleCloseDialog={handleCloseDeleteDialog}
                title={t("delete.user.title")}
                message={deleteMessageText} />)}

            <Box sx={{ margin: "20px 10px" }} >
                <div className="row" style={{ backgroundColor: "#e7ecf1", borderRadius: "5px", padding: "5px" }}>
                    <div className="col-sm-12 " style={{ padding: "20px 15px" }}>
                        <div style={{ display: "flex", alignItems: "center" }}>
                            <h4>{t("search.result")}</h4>
                            <Button
                                onClick={(e) => { showAddEditUserDialog(null) }}
                                variant="contained"
                                size="large"
                                style={{ marginLeft: "auto", textTransform: "none" }}
                            >
                                {t("addUser")}
                            </Button>
                        </div>
                    </div>

                    <div className="col-sm-12" style={{ padding: "10px 15px" }} >

                        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
                            <TableContainer sx={{ maxHeight: 500 }}>
                                <Table stickyHeader aria-label="sticky table">
                                    <TableHead>
                                        <TableRow>
                                            {columns.map((column) => (
                                                <TableCell
                                                    key={column.id}
                                                    align={column.align}
                                                    style={{ minWidth: column.minWidth, fontWeight: 'bold' }}
                                                >
                                                    {column.label}
                                                </TableCell>
                                            ))}
                                            <TableCell style={{ minWidth: 100, fontWeight: 'bold' }}>{t("actions")}</TableCell>

                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {users != null && users.map((row, indx) => {
                                            return (
                                                <TableRow hover role="checkbox" tabIndex={-1} key={indx} >
                                                    {columns.map((column) => {
                                                        const value = row[column.id];
                                                        return (
                                                            <TableCell key={column.id} align={column.align}>
                                                                {column.format ? column.format(value) : value}
                                                            </TableCell>
                                                        );

                                                    })}
                                                    <TableCell>
                                                        <IconButton onClick={() => showAddEditUserDialog(row)}>
                                                            <EditIcon />
                                                        </IconButton>
                                                        <IconButton onClick={() => showDeleteDialogActionPerformed(row)}>
                                                            <ClearIcon />
                                                        </IconButton>

                                                    </TableCell>

                                                </TableRow>
                                            );
                                        })}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                            <TablePagination
                                rowsPerPageOptions={[10, 25, 100]}
                                component="div"
                                count={usersCount}
                                rowsPerPage={rowsPerPage}
                                page={page}
                                onPageChange={handleChangePage}
                                onRowsPerPageChange={handleChangeRowsPerPage}
                            />
                        </Paper>
                    </div>

                </div>
            </Box>
        </>
    )
}

export default UsersPageOperationsPanel