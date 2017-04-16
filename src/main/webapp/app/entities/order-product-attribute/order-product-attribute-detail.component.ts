import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { OrderProductAttribute } from './order-product-attribute.model';
import { OrderProductAttributeService } from './order-product-attribute.service';

@Component({
    selector: 'jhi-order-product-attribute-detail',
    templateUrl: './order-product-attribute-detail.component.html'
})
export class OrderProductAttributeDetailComponent implements OnInit, OnDestroy {

    orderProductAttribute: OrderProductAttribute;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private orderProductAttributeService: OrderProductAttributeService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['orderProductAttribute']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderProductAttributes();
    }

    load(id) {
        this.orderProductAttributeService.find(id).subscribe((orderProductAttribute) => {
            this.orderProductAttribute = orderProductAttribute;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.orderProductAttribute.id !== undefined) {
            this.orderProductAttributeService.update(this.orderProductAttribute)
                .subscribe((res: OrderProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.orderProductAttributeService.create(this.orderProductAttribute)
                .subscribe((res: OrderProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: OrderProductAttribute) {
        this.eventManager.broadcast({ name: 'orderProductAttributeModification', content: 'OK'});
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

    registerChangeInOrderProductAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('orderProductAttributeListModification', (response) => this.load(this.orderProductAttribute.id));
    }
}
