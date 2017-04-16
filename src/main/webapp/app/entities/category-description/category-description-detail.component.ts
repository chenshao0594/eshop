import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { CategoryDescription } from './category-description.model';
import { CategoryDescriptionService } from './category-description.service';

@Component({
    selector: 'jhi-category-description-detail',
    templateUrl: './category-description-detail.component.html'
})
export class CategoryDescriptionDetailComponent implements OnInit, OnDestroy {

    categoryDescription: CategoryDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private categoryDescriptionService: CategoryDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['categoryDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategoryDescriptions();
    }

    load(id) {
        this.categoryDescriptionService.find(id).subscribe((categoryDescription) => {
            this.categoryDescription = categoryDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.categoryDescription.id !== undefined) {
            this.categoryDescriptionService.update(this.categoryDescription)
                .subscribe((res: CategoryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.categoryDescriptionService.create(this.categoryDescription)
                .subscribe((res: CategoryDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: CategoryDescription) {
        this.eventManager.broadcast({ name: 'categoryDescriptionModification', content: 'OK'});
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

    registerChangeInCategoryDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('categoryDescriptionListModification', (response) => this.load(this.categoryDescription.id));
    }
}
