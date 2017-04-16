import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderAccountProduct } from './order-account-product.model';
import { OrderAccountProductService } from './order-account-product.service';

@Component({
    selector: 'jhi-order-account-product-detail',
    templateUrl: './order-account-product-detail.component.html'
})
export class OrderAccountProductDetailComponent implements OnInit, OnDestroy {

    orderAccountProduct: OrderAccountProduct;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderAccountProductService: OrderAccountProductService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderAccountProduct']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderAccountProducts();
    }

    load(id) {
        this.orderAccountProductService.find(id).subscribe((orderAccountProduct) => {
            this.orderAccountProduct = orderAccountProduct;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderAccountProduct.id !== undefined) {
            this.orderAccountProductService.update(this.orderAccountProduct)
                .subscribe((res: OrderAccountProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderAccountProductService.create(this.orderAccountProduct)
                .subscribe((res: OrderAccountProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderAccountProduct) {
        this.eventManager.broadcast({ name: 'orderAccountProductModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderAccountProducts() {
        this.eventSubscriber = this.eventManager.subscribe('orderAccountProductListModification', (response) => this.load(this.orderAccountProduct.id));
    }
}
