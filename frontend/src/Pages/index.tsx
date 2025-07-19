import React from "react";
import Head from "next/head";

export default function Home() {
  return (
    <>
      <Head>
        <title>MiniURL - Shorten your links</title>
      </Head>
      <main className="min-h-screen flex items-center justify-center bg-gray-100 text-center p-4">
        <div className="max-w-xl w-full bg-white p-8 rounded-lg shadow-lg">
          <h1 className="text-3xl font-bold mb-6">Welcome to MiniURL 🚀</h1>
          <p className="text-gray-700">Frontend is set up and Tailwind is working!</p>
          <p className="text-sm text-gray-500 mt-4">Now let’s build the URL shortener UI 🎯</p>
        </div>
      </main>
    </>
  );
}
