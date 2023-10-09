import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import HttpApi from 'i18next-http-backend';
import LanguageDetector from "i18next-browser-languagedetector";
import translationEN from "../../assets/locales/en/translation.json";
import translationRU from "../../assets/locales/ru/translation.json";


const fallbackLng = ["en"];
const availableLanguages = ["en", "ru"];

const resources = {
  en: {
    translation: translationEN
  },
   ru: {
    translation: translationRU
  }
};

i18n
  .use(HttpApi)
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    resources,
    fallbackLng,
    interpolation: {
      escapeValue: false,
    },
    detection: {
      checkWhitelist: true
    },

    debug: false,

    whitelist: availableLanguages,
    react: {
      useSuspense: false,
      wait: false,
    }
    
  });



export default i18n;
