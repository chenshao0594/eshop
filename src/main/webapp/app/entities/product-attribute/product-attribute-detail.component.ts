import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductAttribute } from './product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';

@Component({
    selector: 'jhi-product-attribute-detail',
    templateUrl: './product-attribute-detail.component.html'
})
export class ProductAttributeDetailComponent implements OnInit, OnDestroy {

    productAttribute: ProductAttribute;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productAttributeService: ProductAttributeService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productAttribute']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductAttributes();
    }

    load(id) {
        this.productAttributeService.find(id).subscribe((productAttribute) => {
            this.productAttribute = productAttribute;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productAttribute.id !== undefined) {
            this.productAttributeService.update(this.productAttribute)
                .subscribe((res: ProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productAttributeService.create(this.productAttribute)
                .subscribe((res: ProductAttribute) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductAttribute) {
        this.eventManager.broadcast({ name: 'productAttributeModification', content: 'OK'});
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

    registerChangeInProductAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('productAttributeListModification', (response) => this.load(this.productAttribute.id));
    }
}
