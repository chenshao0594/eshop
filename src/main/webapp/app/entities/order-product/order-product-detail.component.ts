import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderProduct } from './order-product.model';
import { OrderProductService } from './order-product.service';

@Component({
    selector: 'jhi-order-product-detail',
    templateUrl: './order-product-detail.component.html'
})
export class OrderProductDetailComponent implements OnInit, OnDestroy {

    orderProduct: OrderProduct;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderProductService: OrderProductService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderProduct']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderProducts();
    }

    load(id) {
        this.orderProductService.find(id).subscribe((orderProduct) => {
            this.orderProduct = orderProduct;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderProduct.id !== undefined) {
            this.orderProductService.update(this.orderProduct)
                .subscribe((res: OrderProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductService.create(this.orderProduct)
                .subscribe((res: OrderProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProduct) {
        this.eventManager.broadcast({ name: 'orderProductModification', content: 'OK'});
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

    registerChangeInOrderProducts() {
        this.eventSubscriber = this.eventManager.subscribe('orderProductListModification', (response) => this.load(this.orderProduct.id));
    }
}
