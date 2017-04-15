import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { OrderAccountProduct } from './order-account-product.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class OrderAccountProductService {

    private resourceUrl = 'api/order-account-products';
    private resourceSearchUrl = 'api/order-account-products/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(orderAccountProduct: OrderAccountProduct): Observable<OrderAccountProduct> {
        const copy: OrderAccountProduct = Object.assign({}, orderAccountProduct);
        copy.orderAccountProductEndDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductEndDate);
        copy.orderAccountProductStartDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductStartDate);
        copy.orderAccountProductLastStatusDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductLastStatusDate);
        copy.orderAccountProductAccountedDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductAccountedDate);
        copy.orderAccountProductEot = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductEot);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(orderAccountProduct: OrderAccountProduct): Observable<OrderAccountProduct> {
        const copy: OrderAccountProduct = Object.assign({}, orderAccountProduct);
        copy.orderAccountProductEndDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductEndDate);
        copy.orderAccountProductStartDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductStartDate);
        copy.orderAccountProductLastStatusDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductLastStatusDate);
        copy.orderAccountProductAccountedDate = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductAccountedDate);
        copy.orderAccountProductEot = this.dateUtils
            .convertLocalDateToServer(orderAccountProduct.orderAccountProductEot);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<OrderAccountProduct> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.orderAccountProductEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderAccountProductEndDate);
            jsonResponse.orderAccountProductStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderAccountProductStartDate);
            jsonResponse.orderAccountProductLastStatusDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderAccountProductLastStatusDate);
            jsonResponse.orderAccountProductAccountedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderAccountProductAccountedDate);
            jsonResponse.orderAccountProductEot = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.orderAccountProductEot);
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
            jsonResponse[i].orderAccountProductEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderAccountProductEndDate);
            jsonResponse[i].orderAccountProductStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderAccountProductStartDate);
            jsonResponse[i].orderAccountProductLastStatusDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderAccountProductLastStatusDate);
            jsonResponse[i].orderAccountProductAccountedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderAccountProductAccountedDate);
            jsonResponse[i].orderAccountProductEot = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].orderAccountProductEot);
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
