/// <reference types="vite/client" />

//  Для обычных CSS файлов
declare module '*.css' {
    const content: string;
    export default content;
}

// 🔹 Для SCSS модулей
declare module '*.module.scss' {
    const classes: { readonly [key: string]: string };
    export default classes;
}

//  Для изображений и других ассетов
declare module '*.jpg';
declare module '*.jpeg';
declare module '*.png';
declare module '*.svg';
declare module '*.webp';
declare module '*.gif';
