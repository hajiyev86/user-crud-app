import React, { Suspense, useState, createContext } from 'react';
import { Routes, Route } from "react-router-dom";
import Footer from './components/Footer';
import UsersPage from './components/UsersPage';
import NotFound from './components/NotFound';
import './App.css';
import Header from './components/Header';
import { useTranslation } from "react-i18next";
import { Snackbar } from '@mui/material';
import MuiAlert from "@mui/material/Alert";

export const AppContext = createContext();

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function App() {
  const [snackbarMessageText, setSnackbarMessageText] = useState("");
  const [snackbarMessageType, setSnackbarMessageType] = useState("");
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const { t } = useTranslation();

  const handleCloseSnackbar = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackbarOpen(false);
  };

  const showMessageDialog = (messageType, messageText) => {
    setSnackbarMessageText(messageText);
    setSnackbarMessageType(messageType);
    setSnackbarOpen(true);
  }

  const showLoading = (isLoading) => {
    setLoading(isLoading);
  }

  return (
    <Suspense fallback={null}>
      <Snackbar
        open={snackbarOpen}
        autoHideDuration={3000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: "top", horizontal: "right" }}>
        <Alert onClose={handleCloseSnackbar} severity={snackbarMessageType} sx={{ width: "100%" }}>
          {snackbarMessageText}
        </Alert>
      </Snackbar>

      <div className="overlay" style={{ display: loading ? "block" : "none" }}>
        <div className="overlay-content">
          <img src="/images/loading.gif" alt="loading" style={{ width: "120px", paddingTop: "20px" }} />
          <h4 style={{ marginTop: "10px", fontSize: "18px" }}> {t("please.wait")} </h4>
        </div>
      </div>
      <AppContext.Provider value={{ showMessageDialog, showLoading }}>
        <div className="app-wrapper">
          <Header />
          <main>
            <Routes>
              <Route exact path="/" element={<UsersPage />} />
              <Route path="*" element={<NotFound />} />
            </Routes>
          </main>
          <Footer />
        </div>
      </AppContext.Provider>
    </Suspense>
  );
}

export default App;
