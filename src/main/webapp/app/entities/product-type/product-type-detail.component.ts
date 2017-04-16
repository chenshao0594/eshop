import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductType } from './product-type.model';
import { ProductTypeService } from './product-type.service';

@Component({
    selector: 'jhi-product-type-detail',
    templateUrl: './product-type-detail.component.html'
})
export class ProductTypeDetailComponent implements OnInit, OnDestroy {

    productType: ProductType;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productTypeService: ProductTypeService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductTypes();
    }

    load(id) {
        this.productTypeService.find(id).subscribe((productType) => {
            this.productType = productType;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productType.id !== undefined) {
            this.productTypeService.update(this.productType)
                .subscribe((res: ProductType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productTypeService.create(this.productType)
                .subscribe((res: ProductType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductType) {
        this.eventManager.broadcast({ name: 'productTypeModification', content: 'OK'});
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

    registerChangeInProductTypes() {
        this.eventSubscriber = this.eventManager.subscribe('productTypeListModification', (response) => this.load(this.productType.id));
    }
}
