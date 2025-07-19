// frontend/src/components/UrlShortenForm.tsx
"use client";

import { useState } from "react";
import { shortenUrl } from "@/api/shortener";
import { UrlRequest } from "@/types";

interface Props {
  setShortUrl: (url: string) => void;
}

export default function UrlShortenForm({ setShortUrl }: Props) {
  const [originalUrl, setOriginalUrl] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setShortUrl("");
    if (!originalUrl.trim()) {
      setError("Please enter a valid URL");
      return;
    }

    setLoading(true);
    try {
      // Destructure `shortUrl` from the response
      const { shortUrl } = await shortenUrl({ originalUrl } as UrlRequest);
      setShortUrl(shortUrl);
      setOriginalUrl("");
    } catch (err) {
      console.error(err);
      setError("Something went wrong. Try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col space-y-4">
      <input
        type="url"
        placeholder="Enter your long URL"
        value={originalUrl}
        onChange={(e) => setOriginalUrl(e.target.value)}
        className="w-full px-4 py-3 border rounded-lg focus:ring-indigo-500"
        required
      />
      <button
        type="submit"
        disabled={loading}
        className="w-full py-3 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition disabled:opacity-50"
      >
        {loading ? "Shortening..." : "Shorten URL"}
      </button>
      {error && <p className="text-red-500 text-sm text-center">{error}</p>}
    </form>
  );
}
