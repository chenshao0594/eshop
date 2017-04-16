import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductDescription } from './product-description.model';
import { ProductDescriptionService } from './product-description.service';

@Component({
    selector: 'jhi-product-description-detail',
    templateUrl: './product-description-detail.component.html'
})
export class ProductDescriptionDetailComponent implements OnInit, OnDestroy {

    productDescription: ProductDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productDescriptionService: ProductDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductDescriptions();
    }

    load(id) {
        this.productDescriptionService.find(id).subscribe((productDescription) => {
            this.productDescription = productDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productDescription.id !== undefined) {
            this.productDescriptionService.update(this.productDescription)
                .subscribe((res: ProductDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productDescriptionService.create(this.productDescription)
                .subscribe((res: ProductDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductDescription) {
        this.eventManager.broadcast({ name: 'productDescriptionModification', content: 'OK'});
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

    registerChangeInProductDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productDescriptionListModification', (response) => this.load(this.productDescription.id));
    }
}
