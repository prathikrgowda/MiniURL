import React, { useState } from 'react';
import axios from 'axios';

const Home: React.FC = () => {
  const [originalUrl, setOriginalUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/url/shorten', {
        originalUrl,
      });

      setShortUrl(`http://localhost:8080/api/url/${response.data.shortCode}`);
      setError('');
    } catch (err: any) {
      setError('Failed to shorten URL. Please try again.');
      setShortUrl('');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
      <div className="max-w-md w-full bg-white shadow-md rounded px-8 py-6">
        <h2 className="text-2xl font-bold mb-4 text-center">MiniURL Shortener</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="Enter original URL"
            value={originalUrl}
            onChange={(e) => setOriginalUrl(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded mb-4"
            required
          />
          <button
            type="submit"
            className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700"
          >
            Shorten
          </button>
        </form>

        {shortUrl && (
          <div className="mt-4 p-3 bg-green-100 text-green-800 rounded">
            <p className="font-semibold">Shortened URL:</p>
            <a href={shortUrl} target="_blank" rel="noopener noreferrer" className="text-blue-600 underline">
              {shortUrl}
            </a>
          </div>
        )}

        {error && (
          <div className="mt-4 p-3 bg-red-100 text-red-800 rounded">
            {error}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
