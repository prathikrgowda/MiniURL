// frontend/src/api/shortener.ts
import { UrlRequest, UrlResponse } from "@/types";

/**
 * Sends a POST to the backend to shorten a URL.
 * @param request – { originalUrl: string }
 * @returns Promise<UrlResponse> with the full shortened URL.
 */
export async function shortenUrl(request: UrlRequest): Promise<UrlResponse> {
  const res = await fetch("http://localhost:8080/api/url/shorten", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(request),
  });

  if (!res.ok) {
    throw new Error(`Failed to shorten URL (status ${res.status})`);
  }

  return res.json();
}
