import React, { useState } from "react";
import { UrlRequest } from "../types";
import { shortenUrl } from "../api/shortener";
import ShortUrlResult from "./ShortUrlResult";

const UrlShortenerForm: React.FC = () => {
  const [originalUrl, setOriginalUrl] = useState("");
  const [shortCode, setShortCode] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    const request: UrlRequest = { originalUrl };

    try {
      const response = await shortenUrl(request);
      setShortCode(response.shortCode);
    } catch (err: any) {
      setError("Failed to shorten URL. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-xl mx-auto mt-10 p-6 bg-white shadow rounded">
      <h2 className="text-2xl font-semibold mb-4">MiniURL Shortener</h2>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <input
          type="url"
          required
          placeholder="Enter your long URL"
          value={originalUrl}
          onChange={(e) => setOriginalUrl(e.target.value)}
          className="border p-2 rounded"
        />
        <button
          type="submit"
          disabled={loading}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          {loading ? "Shortening..." : "Shorten URL"}
        </button>
      </form>

      {error && <p className="text-red-500 mt-2">{error}</p>}
      {shortCode && <ShortUrlResult code={shortCode} />}
    </div>
  );
};

export default UrlShortenerForm;
