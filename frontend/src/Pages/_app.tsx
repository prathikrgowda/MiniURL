// src/pages/_app.tsx
import "@/styles/globals.css";
import type { AppProps } from "next/app";
import Script from "next/script";

export default function MyApp({ Component, pageProps }: AppProps) {
  return (
    <>
      {/* This will load Tailwind before your React code runs */}
      <Script
        src="https://cdn.tailwindcss.com"
        strategy="beforeInteractive"
      />
      <Component {...pageProps} />
    </>
  );
}
