import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductImageDescription } from './product-image-description.model';
import { ProductImageDescriptionService } from './product-image-description.service';

@Component({
    selector: 'jhi-product-image-description-detail',
    templateUrl: './product-image-description-detail.component.html'
})
export class ProductImageDescriptionDetailComponent implements OnInit, OnDestroy {

    productImageDescription: ProductImageDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productImageDescriptionService: ProductImageDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productImageDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductImageDescriptions();
    }

    load(id) {
        this.productImageDescriptionService.find(id).subscribe((productImageDescription) => {
            this.productImageDescription = productImageDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productImageDescription.id !== undefined) {
            this.productImageDescriptionService.update(this.productImageDescription)
                .subscribe((res: ProductImageDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productImageDescriptionService.create(this.productImageDescription)
                .subscribe((res: ProductImageDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductImageDescription) {
        this.eventManager.broadcast({ name: 'productImageDescriptionModification', content: 'OK'});
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

    registerChangeInProductImageDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productImageDescriptionListModification', (response) => this.load(this.productImageDescription.id));
    }
}
