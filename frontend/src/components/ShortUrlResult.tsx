import React from "react";

interface Props {
  code: string;
}

const ShortUrlResult: React.FC<Props> = ({ code }) => {
  const shortUrl = `${window.location.origin}/${code}`;

  const handleCopy = async () => {
    await navigator.clipboard.writeText(shortUrl);
    alert("Short URL copied to clipboard!");
  };

  return (
    <div className="mt-4 bg-green-50 p-4 rounded">
      <p className="text-lg font-medium">Short URL:</p>
      <div className="flex items-center justify-between mt-2">
        <a
          href={shortUrl}
          target="_blank"
          rel="noopener noreferrer"
          className="text-blue-600 underline"
        >
          {shortUrl}
        </a>
        <button
          onClick={handleCopy}
          className="ml-4 bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
        >
          Copy
        </button>
      </div>
    </div>
  );
};

export default ShortUrlResult;
