// frontend/src/api/shortener.ts
import { UrlRequest, UrlResponse } from "@/types";

export async function shortenUrl(request: UrlRequest): Promise<UrlResponse> {
  const res = await fetch("http://localhost:8080/api/url/shorten", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(request),
  });
  if (!res.ok) {
    throw new Error("Failed to shorten URL");
  }
  return res.json();
}
