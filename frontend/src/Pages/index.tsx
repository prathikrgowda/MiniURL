import Head from "next/head";
import { useState } from "react";
import UrlShortenForm from "../components/UrlShortenForm";
import ShortUrlResult from "../components/ShortUrlResult";

export default function Home() {
  const [shortUrl, setShortUrl] = useState<string>("");

  return (
    <>
      <Head>
        <title>MiniURL – URL Shortener</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
      </Head>

      <main className="min-h-screen flex items-center justify-center bg-gray-100 p-6">
        <div className="bg-white rounded-2xl shadow-lg p-8 w-full max-w-md">
          <h1 className="text-4xl font-extrabold text-center text-gray-800 mb-6">
            MiniURL
          </h1>
          <UrlShortenForm setShortUrl={setShortUrl} />
          {shortUrl && <ShortUrlResult shortUrl={shortUrl} />}
        </div>
      </main>
    </>
  );
}
