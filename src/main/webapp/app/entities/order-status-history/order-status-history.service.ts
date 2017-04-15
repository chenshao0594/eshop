import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { OrderStatusHistory } from './order-status-history.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class OrderStatusHistoryService {

    private resourceUrl = 'api/order-status-histories';
    private resourceSearchUrl = 'api/order-status-histories/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(orderStatusHistory: OrderStatusHistory): Observable<OrderStatusHistory> {
        const copy: OrderStatusHistory = Object.assign({}, orderStatusHistory);
        copy.dateAdded = this.dateUtils
            .convertLocalDateToServer(orderStatusHistory.dateAdded);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(orderStatusHistory: OrderStatusHistory): Observable<OrderStatusHistory> {
        const copy: OrderStatusHistory = Object.assign({}, orderStatusHistory);
        copy.dateAdded = this.dateUtils
            .convertLocalDateToServer(orderStatusHistory.dateAdded);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<OrderStatusHistory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateAdded = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateAdded);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateAdded = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateAdded);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
