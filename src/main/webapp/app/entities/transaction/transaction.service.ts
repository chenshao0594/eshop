import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Transaction } from './transaction.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class TransactionService {

    private resourceUrl = 'api/transactions';
    private resourceSearchUrl = 'api/transactions/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(transaction: Transaction): Observable<Transaction> {
        const copy: Transaction = Object.assign({}, transaction);
        copy.transactionDate = this.dateUtils
            .convertLocalDateToServer(transaction.transactionDate);
        copy.created_date = this.dateUtils
            .convertLocalDateToServer(transaction.created_date);
        copy.last_modified_date = this.dateUtils
            .convertLocalDateToServer(transaction.last_modified_date);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(transaction: Transaction): Observable<Transaction> {
        const copy: Transaction = Object.assign({}, transaction);
        copy.transactionDate = this.dateUtils
            .convertLocalDateToServer(transaction.transactionDate);
        copy.created_date = this.dateUtils
            .convertLocalDateToServer(transaction.created_date);
        copy.last_modified_date = this.dateUtils
            .convertLocalDateToServer(transaction.last_modified_date);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Transaction> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.transactionDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.transactionDate);
            jsonResponse.created_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.created_date);
            jsonResponse.last_modified_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.last_modified_date);
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
            jsonResponse[i].transactionDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].transactionDate);
            jsonResponse[i].created_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].created_date);
            jsonResponse[i].last_modified_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].last_modified_date);
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
