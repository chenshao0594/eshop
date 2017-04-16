import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils, AlertService } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit, OnDestroy {

    template: Template;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private templateService: TemplateService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['template']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTemplates();
    }

    load(id) {
        this.templateService.find(id).subscribe((template) => {
            this.template = template;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.template.id !== undefined) {
            this.templateService.update(this.template)
                .subscribe((res: Template) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.templateService.create(this.template)
                .subscribe((res: Template) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Template) {
        this.eventManager.broadcast({ name: 'templateModification', content: 'OK'});
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

    registerChangeInTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('templateListModification', (response) => this.load(this.template.id));
    }
}
