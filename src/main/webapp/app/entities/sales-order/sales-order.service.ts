import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { SalesOrder } from './sales-order.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class SalesOrderService {

    private resourceUrl = 'api/sales-orders';
    private resourceSearchUrl = 'api/sales-orders/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(salesOrder: SalesOrder): Observable<SalesOrder> {
        const copy: SalesOrder = Object.assign({}, salesOrder);
        copy.orderDateFinished = this.dateUtils
            .convertLocalDateToServer(salesOrder.orderDateFinished);
        copy.lastModified = this.dateUtils
            .convertLocalDateToServer(salesOrder.lastModified);
        copy.datePurchased = this.dateUtils
            .convertLocalDateToServer(salesOrder.datePurchased);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(salesOrder.createdDate);
        copy.lastModifiedDate = this.dateUtils
            .convertLocalDateToServer(salesOrder.lastModifiedDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(salesOrder: SalesOrder): Observable<SalesOrder> {
        const copy: SalesOrder = Object.assign({}, salesOrder);
        copy.orderDateFinished = this.dateUtils
            .convertLocalDateToServer(salesOrder.orderDateFinished);
        copy.lastModified = this.dateUtils
            .convertLocalDateToServer(salesOrder.lastModified);
        copy.datePurchased = this.dateUtils
            .convertLocalDateToServer(salesOrder.datePurchased);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(salesOrder.createdDate);
        copy.lastModifiedDate = this.dateUtils
            .convertLocalDateToServer(salesOrder.lastModifiedDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<SalesOrder> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.orderDateFinished = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderDateFinished);
            jsonResponse.lastModified = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.lastModified);
            jsonResponse.datePurchased = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.datePurchased);
            jsonResponse.createdDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.createdDate);
            jsonResponse.lastModifiedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.lastModifiedDate);
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
            jsonResponse[i].orderDateFinished = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderDateFinished);
            jsonResponse[i].lastModified = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].lastModified);
            jsonResponse[i].datePurchased = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].datePurchased);
            jsonResponse[i].createdDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].createdDate);
            jsonResponse[i].lastModifiedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].lastModifiedDate);
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
