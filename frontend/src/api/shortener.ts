import axios from "axios";
import { UrlRequest, UrlResponse } from "../types";

const API_BASE = "http://localhost:8080/api/url";

export const shortenUrl = async (data: UrlRequest): Promise<UrlResponse> => {
  const response = await axios.post<UrlResponse>(`${API_BASE}/shorten`, data);
  return response.data;
};

export const getOriginalUrl = async (code: string): Promise<string> => {
  const response = await axios.get(`${API_BASE}/${code}`);
  return response.request.responseURL;
};
