import React, {useState, useEffect, useContext} from "react";
import userServices from "../services/user.services";
import SearchPanel from "./users/UsersPageSearchPanel";
import OperationsPanel from "./users/UsersPageOperationsPanel";
import {AppContext} from '../App';

const initialSearchData = {
    phoneNumber: "",
    email: "",
    name: ""
};

const UsersPage = () => {
    const {showMessageDialog, showLoading} = useContext(AppContext);
    const [searchData, setSearchData] = useState(initialSearchData);
    const [users, setUsers] = useState([]);
    const [usersCount, setUsersCount] = useState(0);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    //-------------------------Search page operations------------------------------------
    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const handleChangeSearchData = (e) => {
        if (e.target.name === "phoneNumber") {
            //  const plusAndOnlyNums = e.target.value.replace(/[^0-9+]/g, "");
            const plusAndOnlyNums = e.target.value.replace(/[^+\d\- ]/g, '');
            setSearchData((prevState) => {
                return {
                    ...prevState,
                    [e.target.name]: plusAndOnlyNums,
                };
            });
        } else {
            setSearchData((prevState) => {
                return {
                    ...prevState,
                    [e.target.name]: e.target.value,
                };
            });
        }
    }

    const retrieveDatas = (searchParams) => {
        if (!searchParams)
            searchParams = searchData;
        let params = {};
        params["name"] = searchParams.name;
        params["email"] = searchParams.email;
        params["phoneNumber"] = searchParams.phoneNumber;
        if (page) {
            params["page"] = page;
        } else {
            params["page"] = 0;
        }
        if (rowsPerPage) {
            params["rowSizePerPage"] = rowsPerPage;
        }
        showLoading(true);

        userServices.postSearchUsers(params).then(
            (response) => {
                showLoading(false);
                const {rows, total} = response.data;
                setUsersCount(total);
                setUsers(rows);
            },
            (error) => {
                showLoading(false);
                const errorMessage = error.response?.data?.message || error.message || 'An error occurred.';
                console.log(errorMessage);
                showMessageDialog("error", errorMessage);
            }
        );
    };

    useEffect( retrieveDatas, [page, rowsPerPage]);

    const searchUsersActionPerformed = (e) => {
        e.preventDefault();
        //to prevent 2 time fetching
        if (page !== 0) {
            setPage(0);
        } else {
            retrieveDatas();
        }
    }

    const clearSearchActionPerformed = (e) => {
        e.preventDefault();
        setSearchData(initialSearchData);
        //to prevent 2 time fetching
        if (page !== 0) {
            setPage(0);
        } else {
            retrieveDatas(initialSearchData);
        }
    }
    //-------------------------End of Search Users Operations---------------------------------

    return (
        <div className="row">
            <div className="col-md-3 ">
                <SearchPanel
                    searchData={searchData}
                    handleChangeSearchData={handleChangeSearchData}
                    searchUsersActionPerformed={searchUsersActionPerformed}
                    clearSearchActionPerformed={clearSearchActionPerformed}/>
            </div>
            <div className="col-md-9 ">
                <OperationsPanel
                    users={users}
                    usersCount={usersCount}
                    page={page}
                    rowsPerPage={rowsPerPage}
                    handleChangePage={handleChangePage}
                    handleChangeRowsPerPage={handleChangeRowsPerPage}
                    handleRefreshResult={retrieveDatas}
                />
            </div>
        </div>
    )
}

export default UsersPage;