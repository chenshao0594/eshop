import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContentDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/content-description/content-description-detail.component';
import { ContentDescriptionService } from '../../../../../../main/webapp/app/entities/content-description/content-description.service';
import { ContentDescription } from '../../../../../../main/webapp/app/entities/content-description/content-description.model';

describe('Component Tests', () => {

    describe('ContentDescription Management Detail Component', () => {
        let comp: ContentDescriptionDetailComponent;
        let fixture: ComponentFixture<ContentDescriptionDetailComponent>;
        let service: ContentDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [ContentDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContentDescriptionService,
                    EventManager
                ]
            }).overrideComponent(ContentDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContentDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContentDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContentDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.contentDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
