import axios from "axios";
import uuid from "uuid/v4";
import Urls from "../resources/Urls";
declare var tsConfig: any;

export default class FormatAmountService {

    public static formatAmount(data: any = {}) {
        let url = `${Urls.FORMAT_AMOUNT_URL}`;
        let headers = FormatAmountService.getHeaders()

        const instance = axios.create({
            headers: headers,
        });

        data.header = {
            groupId: headers.groupId,
            messageId: headers.messageId,
            timestamp:  headers.timestamp,
        };

        return instance.post(url, data)
        // return "KES 1,000.99"
    }

    private static getHeaders = () => {
        const headers = {
            "groupId": uuid(),
            "messageId" : uuid(),
            "timestamp" : new Date().toISOString(),
            "Authorization" : tsConfig.API_KEY,
            "Content-Type": "application/json",
            "sessionId": localStorage.getItem("sessionId"),
        };
        return headers;
    }
}
