import React from "react";

interface Props { shortUrl: string }

export default function ShortUrlResult({ shortUrl }: Props) {
  const handleCopy = () => {
    navigator.clipboard.writeText(shortUrl);
    alert("Copied to clipboard!");
  };

  return (
    <div className="mt-6 bg-green-50 border border-green-200 rounded-lg p-4 flex items-center justify-between">
      <a
        href={shortUrl}
        target="_blank"
        rel="noopener noreferrer"
        className="text-indigo-600 underline break-all"
      >
        {shortUrl}
      </a>
      <button
        onClick={handleCopy}
        className="ml-4 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
      >
        Copy
      </button>
    </div>
  );
}
